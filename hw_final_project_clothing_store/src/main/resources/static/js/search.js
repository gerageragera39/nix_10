$(document).ready(function () {
    $('#clothesNamed').autocomplete({
            source: function (request, response) {
                $.get("http://localhost:8080/clothes/suggestions?", { query: request.term }, function (data, status) {
                    $("#results").html("");
                    if (status === 'success') {
                        response(data);
                    }
                });
            }
        }
    );
})
