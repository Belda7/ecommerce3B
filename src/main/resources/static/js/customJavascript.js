window.onload = () => {
    if (document.getElementById("searchValue")) {
        const searchTag = document.getElementById("searchValue");
        searchTag.onchange = () => {
            let searchValue = searchTag.value;
            document.getElementById("search").setAttribute("href", "/products/searchByWord/" + searchValue)
        }
    }

    if (document.getElementById("addButtonSN"))
        document.getElementById("addButtonSN").onclick = () => addInput("socialNetworks", "socials");

    if (document.getElementById("removeButtonSN"))
        document.getElementById("removeButtonSN").onclick = () => removeInput("socials");

    if (document.getElementById("addButtonAD"))
        document.getElementById("addButtonAD").onclick = () => addInput("addresses", "addresses");

    if (document.getElementById("removeButtonAD"))
        document.getElementById("removeButtonAD").onclick = () => removeInput("addresses");
}

function addInput(name, id) {
    let input = document.createElement('input');
    input.setAttribute('type', 'text');
    input.setAttribute('name', name)
    document.getElementById(id).appendChild(input);
}

function removeInput(id) {
    const inputsParent = document.getElementById(id);
    inputsParent.removeChild(inputsParent.lastChild)
}