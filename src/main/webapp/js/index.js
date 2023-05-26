$(document).ready(function(){
    // Adding CSRF token to AJAX header
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(function () {
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    function comparer(index) {
        return function(a, b) {
        var valA = getCellValue(a, index), valB = getCellValue(b, index)
        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
        }
    }
    function getCellValue(row, index){ return $(row).children('td').eq(index).text() }

    /*
     * Selecting a table header
     * -sorts table based on selected header
     * -sets asc/desc triangle on selected header
    */
    var lastTH = "";
    $('th').on('click', function(){
        var table = $(this).parents('table').eq(0)
        var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()))
        if (lastTH == this || lastTH == ""){this.asc = !this.asc} else {this.asc = true}
        if (!this.asc){rows = rows.reverse()}
        for (var i = 0; i < rows.length; i++){table.append(rows[i])}

        // Remember how the column is currently sorted
        $('th').removeClass(["th-sort-asc","th-sort-desc"])
        $(this).toggleClass("th-sort-asc", this.asc)
        $(this).toggleClass("th-sort-desc", !this.asc)
        lastTH = this
    })

    /*
     * Selecting A Position
     * -sends you to the candidatesposition page (Position Details page)
     */
    $('.positionName').on('click', function(){
        positionID = $(this).closest('tr').attr('id')   // retrieves the SQL id of the position
        $.ajax({
            type: "GET",
            url: '/candidatesposition?id=' + positionID,
            success: function (position) {
                window.location = "/candidatesposition?id=" + positionID
            },

            failure: function (errMsg) {
                console.log(errMsg.toString())
            }
        });
    })
    /*
     * Selecting Search Positions
     * -sends you to the Postions Page
     */
    $('.positions').on('click', function(){
        $.ajax({
            type: "GET",
            url: '/positions',
            success: function (position) {
                window.location = "/positions"
            },

            failure: function (errMsg) {
                console.log(errMsg.toString())
            }
        });
    })
});
