package com.guen.program.shop.service;

import com.guen.program.shop.exception.ItemNotFindException;
import com.guen.program.shop.model.dto.request.ReqItemDto;
import com.guen.program.shop.model.dto.request.ReqUpdateItemDto;
import com.guen.program.shop.model.dto.response.ItemDto;
import com.guen.program.shop.model.entity.Category;
import com.guen.program.shop.model.entity.inheritance.Coin;
import com.guen.program.shop.model.entity.inheritance.Item;
import com.guen.program.shop.model.entity.inheritance.Stock;
import com.guen.program.shop.model.enumclass.BatType;
import com.guen.program.shop.repository.category.CategoryRepo;
import com.guen.program.shop.repository.item.ItemRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ItemService {
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @PersistenceContext
    EntityManager em;


    @Transactional
    public void saveItem(final ReqItemDto reqStockDto) {
        List<Long> idList = reqStockDto.getCategories().stream().map(c -> c.getId()).collect(Collectors.toList());
        List<Category> categories = categoryRepo.findByIdIn(idList);
        Item item = reqStockDto.getBatType() == BatType.STOCK ?
                Stock.builder()
                        .price(reqStockDto.getPrice())
                        .stockQuanitty(reqStockDto.getStockQuanitty())
                        .ticker(reqStockDto.getTicker())
                        .name(reqStockDto.getName())
                        .build() :
                Coin.builder()
                        .company(reqStockDto.getCompany())
                        .price(reqStockDto.getPrice())
                        .stockQuanitty(reqStockDto.getStockQuanitty())
                        .name(reqStockDto.getName())
                        .build();
        itemRepo.save(item);
        categories.forEach(category -> category.getItems().add(item));
        categoryRepo.saveAll(categories);
    }

    public List<ItemDto> findAll() {
        List<ItemDto> itemDtos = itemRepo.findAll().stream()
                .map(i -> {
                    String company = "";
                    String ticker = "";
                    BatType batType = null;

                    // 엔티티가 Coin 타입일 때
                    if (i instanceof Coin) {
                        company = ((Coin) i).getCompany();
                        batType = BatType.COIN;
                    }
                    // 엔티티가 Stock 타입일 때
                    else if (i instanceof Stock) {
                        ticker = ((Stock) i).getTicker();
                        batType = BatType.STOCK;
                    }

                    // 카테고리 매핑
                    List<ItemDto.ItemCategory> categories = i.getCategories().stream()
                            .map(c -> ItemDto.ItemCategory.builder().id(c.getId()).name(c.getName()).build())
                            .collect(Collectors.toList());

                    // ItemDto 객체 생성
                    return ItemDto.builder()
                            .id(i.getId())
                            .ticker(ticker)
                            .company(company)
                            .price(i.getPrice())
                            .stockQuanitty(i.getStockQuanitty())
                            .name(i.getName())
                            .batType(batType)
                            .categories(categories)
                            .build();
                })
                .collect(Collectors.toList());
        return itemDtos;
    }

    public ItemDto findById(final Long id){
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new ItemNotFindException("item이 없습니다."));

        List<ItemDto.ItemCategory> categories = item.getCategories().stream()
                .map(category -> ItemDto.ItemCategory.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());

        BatType batType = (item instanceof Stock) ? BatType.STOCK : BatType.COIN;
        String ticker = (item instanceof Stock) ? ((Stock) item).getTicker() : "";
        String company = (item instanceof Coin) ? ((Coin) item).getCompany() : "";

        return ItemDto.builder()
                .id(item.getId())
                .batType(batType)
                .categories(categories)
                .stockQuanitty(item.getStockQuanitty())
                .name(item.getName())
                .price(item.getPrice())
                .ticker(ticker)
                .company(company)
                .build();
    }

    @Transactional
    public void updateItem(final Long id ,ReqUpdateItemDto reqStockDto) {
        List<Long> categoryIdList = reqStockDto.getCategories().stream().map(c -> c.getId()).collect(Collectors.toList());
        List<Category> newBaseCategories = categoryRepo.findByIdIn(categoryIdList);
        BatType batType = reqStockDto.getBatType();
        Item item = itemRepo.findById(id).orElseThrow(() -> new ItemNotFindException("item 없네"));
        if(batType.equals(BatType.STOCK)){
            Stock stock = (Stock) item;
            stock.editTicker(reqStockDto.getTicker());
            stock.editStockQuanitty(reqStockDto.getStockQuanitty());
            stock.editPrice(reqStockDto.getPrice());
            stock.editName(reqStockDto.getName());
        }else{
            Coin coin = (Coin) item;
            coin.editCompany(reqStockDto.getCompany());
            coin.editStockQuanitty(reqStockDto.getStockQuanitty());
            coin.editPrice(reqStockDto.getPrice());
            coin.editName(reqStockDto.getName());
        }
        itemRepo.save(item);

        List<Category> originCategories = item.getCategories();
        List<Category> newCategories = newBaseCategories;
        // 추가할 카테고리 필터링
        List<Category> addCategories = newCategories.stream()
                .filter(newCategory -> !originCategories.contains(newCategory))
                .collect(Collectors.toList());

        // 제거할 카테고리 필터링
        List<Category> removeCategories = originCategories.stream()
                .filter(originalCategory -> !newCategories.contains(originalCategory))
                .collect(Collectors.toList());

        // 신규 카테고리 추가
        addCategories.forEach(category -> {
            category.addItem(item);
            categoryRepo.save(category);
        });

        // 기존 카테고리 제거
        removeCategories.forEach(category -> {
            category.removeItem(item);
            categoryRepo.save(category);
        });

    }

    @Transactional
    public void deleteById(final Long itemId) {
        Item item = itemRepo.findById(itemId).orElseThrow(()-> new ItemNotFindException("아이템이 없어요..!"));
        item.getCategories().forEach(category -> {
            category.removeItem(item);
            categoryRepo.save(category);
        });
        itemRepo.deleteById(itemId);
    }
}
