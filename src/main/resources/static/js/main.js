function showForm(element) {
    element.style.display = 'block';
}

var addButton = document.getElementById('addPlannerButton');
var addForm = document.getElementById('addPlannerForm');

addButton.addEventListener('click', function() {
    showForm(addForm);
});

var putPlannerButtons = document.getElementsByClassName('putPlannerButton');

for (var i = 0; i < putPlannerButtons.length; i++) {
    putPlannerButtons[i].addEventListener("click", function (event) {
        var putForm = event.target.previousElementSibling;
        if (putForm) {
            showForm(putForm);
        }
    });
}