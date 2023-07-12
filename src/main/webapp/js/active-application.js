$(document).ready(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    /*
     * Adding CSRF token to AJAX header
     */
    $(function () {
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    /*
     * Sorting activeRecruitingPositions table once the document is loaded
     * - attempted to do so using some sort of event listener (after the table is loaded),
     *   but couldn't get that to work
    */
    var table = $('#active_recruiting_positions')
    var rows = table.find('tr:gt(0)').toArray().sort(comparer(0))
    for (var i = 0; i < rows.length; i++){table.append(rows[i])}

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
     * Selecting a table row
     * -highlights row
    */
    var selectedID = null;
    $('.contentTable tbody tr').on('click', function(){
        $('tbody tr').removeClass("tr-sel")
        $(this).toggleClass("tr-sel")
        selectedID = $(this).attr('id')
    })

    /*
     * Selecting Close button
     * -archives highlighted position
     * -removes highlighted position from the table
    */
    $('#button_close_position').on('click', function(){
        if (selectedID != null) {
            if (window.confirm("Are you sure you want to archive this position?")) {
                $.ajax({
                    type: "PUT",
                    url: '/recruiting',
                    data: { "selectedID": selectedID },
                    success: function (position) {
                        selectedID = null
                        $(".tr-sel").remove()
                        console.log("Position successfully closed.")
                    },

                    failure: function (errMsg) {
                        console.log(errMsg.toString())
                    }
                });
            }
        }
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
});