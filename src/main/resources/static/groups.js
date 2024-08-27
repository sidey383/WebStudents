const groupPath = "/api/group";

document.addEventListener("DOMContentLoaded", ready);

let pageNumber = 0;
let pageSize = 2;
let pageCount = 0

function ready() {
    const urlParams = new URLSearchParams(window.location.search);
    const numberParam = parseInt(urlParams.get('pageNumber'), 10);
    if (!isNaN(numberParam) && numberParam >= 0) {
        pageNumber = numberParam
    }
    updatePage()
    updateButtons()
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
    getGroups()
        .then(answer => {
            updateTable(answer.data);
            pageCount = answer.pageCount;
            updateButtons();
            updatePageNumberText()
        })
}

function updatePageNumberText() {
    const text = document.getElementById("pageNum");
    text.innerText = "Page: " + (pageNumber + 1) + "/" + (pageCount);
}

function updateTable(data) {
    var tbody = document.getElementById("groups");
    var children = []
    data.forEach(group => {
        var row = document.createElement("tr")
        var number = row.insertCell()
        var studentCount = row.insertCell()
        var action = row.insertCell()
        number.appendChild(document.createTextNode(group.number))
        studentCount.appendChild(document.createTextNode(group.studentCount))
        var groupLink = document.createElement('a');
        var linkText = document.createTextNode('Edit');
        groupLink.appendChild(linkText)
        groupLink.href = "/group?id=" + group.id;
        action.appendChild(groupLink)
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

function getGroups() {
    return fetch(
        groupPath + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
        {
            headers: { "Content-Type": "application/json" },
            method: "GET"
        }
    )
        .then(res => res.ok ? res : Promise.reject(res))
        .then(response => response.json())
        .catch(res => console.log(res))
}