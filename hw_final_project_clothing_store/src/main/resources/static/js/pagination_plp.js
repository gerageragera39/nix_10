function runPagination(page, size, pageOperator) {
    let pageData = document.getElementById('pageData');
    const sort = pageData.getAttribute('data-sort');
    const order = pageData.getAttribute('data-order');
    submitRequest(sort, order, page + pageOperator, size);
}

function runSort(sort, order) {
    if (order === 'desc') {
        order = 'asc';
    } else {
        order = 'desc';
    }
    let pageData = document.getElementById('pageData');
    const page = pageData.getAttribute('data-page');
    const size = pageData.getAttribute('data-size');
    submitRequest(sort, order, page, size);
}

function submitRequest(sort, order, page, size) {

    var kvp = document.location.search.substr(1).split('&');

    let personalSearchSubmit = document.getElementById('personalSearchSubmit');
    if (personalSearchSubmit !== null) {
        let personalSearch = document.getElementById('personalSearch');
        if (personalSearch !== null) {
            let input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "sort");
            input.setAttribute("value", sort);
            personalSearch.appendChild(input);
            input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "order");
            input.setAttribute("value", order);
            personalSearch.appendChild(input);
            input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "page");
            input.setAttribute("value", page);
            personalSearch.appendChild(input);
            input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "size");
            input.setAttribute("value", size);
            personalSearch.appendChild(input);

            let i = 0;
            for (; i < kvp.length; i++) {
                if (kvp[i].startsWith('type' + '=') ||
                    kvp[i].startsWith('brand' + '=') ||
                    kvp[i].startsWith('color' + '=') ||
                    kvp[i].startsWith('search_clothes' + '=') ||
                    kvp[i].startsWith('clothes_size' + '=') ||
                    kvp[i].startsWith('sex' + '=')
                    // kvp[i].startsWith('sort' + '=')
                ){
                    let pair = kvp[i].split('=');
                    input = document.createElement("input");
                    input.setAttribute("type", "hidden");
                    input.setAttribute("name", pair[0]);
                    input.setAttribute("value", pair[1]);
                    personalSearch.appendChild(input);
                }
            }

            personalSearchSubmit.click();
        }
    }
}

function deleteParams(page, size, pageOperator) {
    let pageData = document.getElementById('pageData');
    const sort = pageData.getAttribute('data-sort');
    const order = pageData.getAttribute('data-order');
    submitDelete(sort, order, page + pageOperator, size);
}

function submitDelete(sort, order, page, size) {
    let personalSearchSubmit = document.getElementById('personalSearchSubmit');
    if (personalSearchSubmit !== null) {
        let personalSearch = document.getElementById('personalSearch');
        if (personalSearch !== null) {
            let input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "sort");
            input.setAttribute("value", sort);
            personalSearch.appendChild(input);
            input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "order");
            input.setAttribute("value", order);
            personalSearch.appendChild(input);
            input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "page");
            input.setAttribute("value", page);
            personalSearch.appendChild(input);
            input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "size");
            input.setAttribute("value", size);
            personalSearch.appendChild(input);
            personalSearchSubmit.click();
        }
    }
}