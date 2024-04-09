class CustomError extends Error {
  constructor(message, code , status) {
    super(message);
    this.response = {};
    this.response.data = {
      code: code,
      message: message,
      status: status,
    };
  }
}
export default CustomError;