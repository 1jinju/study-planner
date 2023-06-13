function getTodayDate() {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth() + 1;
    var day = today.getDate();

    // 날짜 형식 변환
    var formattedDate = year + "년 " + month + "월 " + day + "일";
    return formattedDate;
}

document.getElementById("todayDate").textContent = getTodayDate();