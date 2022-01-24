function addParam(param, value) {
    var localParams = document.location.search;
    var url = new URL("http://localhost:8080/clothes");

    if (localParams === "") {
        localParams = '?';
    }

    url = url + localParams;

    url.searchParams.append(param, value);
}

function insertParam(key, value) {
    key = encodeURIComponent(key);
    value = encodeURIComponent(value);

    var kvp = document.location.search.substr(1).split('&');

    let i = 0;
    for (; i < kvp.length; i++) {
        if (kvp[i].startsWith(key + '=')) {
            let pair = kvp[i].split('=');
            pair[1] = value;
            kvp[i] = pair.join('=');
            break;
        }
    }
    if (i >= kvp.length) {
        kvp[kvp.length] = [key, value].join('=');
    }

    i = 0;
    for (; i < kvp.length; i++) {
        if (kvp[i].startsWith('page=')) {
            let pair = kvp[i].split('=');
            pair[1] = 1;
            kvp[i] = pair.join('=');
            break;
        }
    }


    let params = kvp.join('&');
    // params = params + '&page=1';
    document.location.search = params;

    // rollbackPage('page', 1);
}

function rollbackPage(key, value) {
    key = encodeURIComponent(key);
    value = encodeURIComponent(value);

    var kvp = document.location.search.substr(1).split('&');

    let i = 0;
    for (; i < kvp.length; i++) {
        if (kvp[i].startsWith(key + '=')) {
            let pair = kvp[i].split('=');
            pair[1] = value;
            kvp[i] = pair.join('=');
            break;
        }
    }
    if (i >= kvp.length) {
        kvp[kvp.length] = [key, value].join('=');
    }

    let params = kvp.join('&');
    document.location.search = params;
}