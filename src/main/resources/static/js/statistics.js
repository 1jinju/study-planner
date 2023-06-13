const goalAchievementRateElement = document.getElementById('goalAchievementRateValue');
const goalAchievementRate = parseFloat(goalAchievementRateElement.textContent);

const progress = document.querySelector('.progress');
progress.style.width = `${goalAchievementRate}%`;