const groupPath = "/api/group";

const studentPath = "/api/student";

document.addEventListener("DOMContentLoaded", ready);

let pageNumber = 0;
let pageSize = 20;
let pageCount = 0;
let groupId = 1;

let group

function ready() {
    const link = window.location.href
    const urlParams = new URLSearchParams(window.location.search);
    const numberParam = parseInt(urlParams.get('pageNumber'), 10);
    const idParam = parseInt(link.split("/").at(-1));
    if (!isNaN(numberParam) && numberParam >= 0) {
        pageNumber = numberParam
    }
    if (!isNaN(groupId)) {
        groupId = idParam;
    }
    updateGroup()
    updatePage()
}

function updateGroup() {
    getGroup()
        .then(group => {
            const h = document.getElementById("group-name")
            h.innerText = "Группа " + group.number;
        })
}

function nextPage() {
    if (pageNumber < pageCount - 1 || pageNumber === 0) {
        pageNumber++;
        updatePage()
    }
}

function prevPage() {
    if (pageNumber > 0) {
        pageNumber--;
        updatePage()
    }
}

function updatePage() {
    getStudents()
        .then(json => {
            updateTable(json.data)
            pageCount = json.pageCount;
            updateButtons();
            updatePageNumberText()
        })
}

function updatePageNumberText() {
    const text = document.getElementById("pageNum");
    text.innerText = "Страница: " + (pageNumber + 1) + "/" + (pageCount);
}

function updateTable(data) {
    const tbody = document.getElementById("students");
    const children = [];
    data.forEach(student => {
        var row = document.createElement("tr")
        var dateField = row.insertCell()
        var fullName = row.insertCell()
        var action = row.insertCell()
        const date = new Date(student.createTime)
        dateField.appendChild(document.createTextNode(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()));
        fullName.appendChild(document.createTextNode(student.fullName))
        var deleteButton = document.createElement('button');
        deleteButton.appendChild(document.createTextNode('Delete'))
        deleteButton.addEventListener('click', () => deleteStudent(student.id))
        action.appendChild(deleteButton)
        children.push(row)
    })
    tbody.replaceChildren(...children)
}

function updateButtons() {
    const nextButton = document.getElementById("next-butt");
    const prevButton = document.getElementById("prev-butt");
    if (pageNumber < pageCount - 1) {
        nextButton.style.visibility = 'visible'
    } else {
        nextButton.style.visibility = 'hidden'
    }
    if (pageNumber > 0) {
        prevButton.style.visibility = 'visible'
    } else {
        prevButton.style.visibility = 'hidden'
    }
}

function getGroup() {
    return fetch(
        groupPath + "/" + groupId,
        {
            headers: { "Content-Type": "application/json" },
            method: "GET"
        }
    )
        .then(res => res.ok ? res : Promise.reject(res))
        .then(response => response.json())
        .catch(res => console.log(res))
}

function getStudents() {
    return fetch(
        studentPath + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize + "&group=" + groupId,
        {
            headers: { "Content-Type": "application/json" },
            method: "GET"
        }
    )
        .then(res => res.ok ? res : Promise.reject(res))
        .then(response => response.json())
        .catch(res => console.log(res))
}

function createStudent() {
    const fullName = document.getElementById("fullName").value;
    fetch(
        studentPath,
        {
            headers: { "Content-Type": "application/json" },
            method: "POST",
            body: JSON.stringify({ fullName: fullName, group: groupId })
        }
    )
        .then(res => res.ok ? res : Promise.reject(res))
        .then(res => res.json())
        .then(() => updatePage())
        .catch(res => console.log(res))
}

function deleteStudent(studentId) {
    fetch(
        studentPath + "/" + studentId,
        {
            headers: { "Content-Type": "application/json" },
            method: "DELETE"
        }
    )
        .then(res => res.ok ? res : Promise.reject(res))
        .then(() => updatePage())
        .catch(res => console.log(res))
}
