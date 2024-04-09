<template>
  <div class="main">
    <div
        class="dropzone-container"
        @dragover="dragover"
        @dragleave="dragleave"
        @drop="drop"
    >
      <input
          type="file"
          multiple
          name="file"
          id="fileInput"
          class="hidden-input"
          @change="onChange"
          ref="fileInput"
          accept=".pdf,.jpg,.jpeg,.png"
      />

      <label for="fileInput" class="file-label">
        <div v-if="isDragging">Release to drop files here.</div>
        <div v-else>Drop files here or <u>click here</u> to upload.</div>
      </label>

      <div class="preview-container mt-4" v-if="files.length">
        <div v-for="file in files" :key="file.name" class="preview-card">
          <div>
            <img class="preview-img" :src="generateThumbnail(file)" />
            <p :title="file.name">
              {{ makeName(file.name) }}
            </p>
          </div>
          <div>
            <button
                class="ml-2"
                type="button"
                @click="remove(files.indexOf(file))"
                title="Remove file"
            >
              <b>&times;</b>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {ref, onMounted} from 'vue';

export default {
  setup(props,Context) {

    // Reactive data
    const isDragging = ref(true);
    const files = ref([]);
    const fileInput = ref(null);

    const generateThumbnail = (file) => {
      let fileSrc = URL.createObjectURL(file);
      setTimeout(() => {
        URL.revokeObjectURL(fileSrc);
      }, 1000);
      return fileSrc;
    };

    const makeName = (name) => {
      return (
          name.split(".")[0].substring(0, 3) +
          "..." +
          name.split(".")[name.split(".").length - 1]
      );
    };

    const remove = (i) => {
      files.value.splice(i, 1);
      console.log("file size:" + files.value.length);
      //변경된 파일을 상위 컴포넌트에 전달

      Context.emit('updateFiles',files);
    };

    const dragover = (e) => {
      e.preventDefault();
      isDragging.value = true;
    };

    const dragleave = () => {
      isDragging.value = true;
    };


    // Methods
    const onChange = () => {
      files.value = [...fileInput.value.files];
      //변경된 파일을 상위 컴포넌트에 전달
      Context.emit('updateFiles',files);
    };
    const drop = (e) => {
      e.preventDefault();
      fileInput.value.files = e.dataTransfer.files;
      onChange();
      isDragging.value = false;
    };

    // Lifecycle hook
    onMounted(() => {
      console.log('Component mounted');
    });

    return {
      isDragging,
      files,
      onChange,
      generateThumbnail,
      makeName,
      remove,
      dragover,
      dragleave,
      drop,
      fileInput
    };
  }
}
</script>

<style scoped>
.main {
  display: flex;
  flex-grow: 1;
  align-items: left;
  height: 18vh;
  justify-content: left;
  text-align: left;
  margin-bottom: 2rem;
}
.dropzone-container {
  padding: 4rem;
  background: #f7fafc;
  border: 1px solid #e2e8f0;
}
.hidden-input {
  opacity: 0;
  overflow: hidden;
  position: absolute;
  width: 1px;
  height: 1px;
}
.file-label {
  font-size: 20px;
  display: block;
  cursor: pointer;
}
.preview-container {
  display: flex;
  margin-top: 2rem;
}
.preview-card {
  display: flex;
  border: 1px solid #a2a2a2;
  padding: 5px;
  margin-left: 1px;
}
.preview-img {
  width: 50px;
  height: 50px;
  border-radius: 5px;
  border: 1px solid #a2a2a2;
  background-color: #a2a2a2;
}
</style>