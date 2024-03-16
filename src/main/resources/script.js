function checkInputs() {
    var input1 = document.getElementById("manufacturer").value;
    if(input1 === "") {
    alert("Необходимо заполнить все поля перед добавлением данных в смету.");
    return false;
    }
}