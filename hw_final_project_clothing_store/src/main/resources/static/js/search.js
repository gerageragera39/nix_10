$(document).ready(function () {
    $('#bookNamed').autocomplete({
            source: function (request, response) {
                $.get("http://localhost:8080/books/suggestions?", { query: request.term }, function (data, status) {
                    $("#results").html("");
                    if (status === 'success') {
                        response(data);
                    }
                });
            }
        }
    );
})
