/**
 * 将错误存储到浏览器中
 * @param data
 */
function setErrorDataToLocalStorage(data){
    localStorage.setItem("errorCode", data.responseCode)
    localStorage.setItem("errorMessage", data.detailed)
    window.location.href="error.html";
}