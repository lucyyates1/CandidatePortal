$(document).ready(function(){
    // Adding CSRF token to AJAX header
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    const queryString = window.location.search
    const urlParams = new URLSearchParams(queryString)
    $(function () {
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });
    $("#candidate-form").submit(function (event) {
        event.preventDefault();
        post_application($(this));
    });
    $('#upload-resume').submit(function (event) {
        event.preventDefault();
        positionID = urlParams.get('id')   // retrieves the url param id of the position
        post_resume($(this), positionID);
    });
});

function post_resume(form, positionID){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);

    $.ajax({
        type: "POST",
        url: '/addResume',
        processData: false,
        contentType: false,
        data: formdata,

        success: function (response, textStatus) {
            console.log(response);
            window.location.replace("/applyposition?id=" + positionID);
        },

        error: function (errMsg) {
            console.log(errMsg.toString())
        }
    });
}
function post_application(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);
    $.ajax({
        type: "POST",
        url: '/applyposition',
        processData: false,
        contentType: false,
        data: formdata ? formdata : form.serialize(),

        success: function (response, textStatus) {
            console.log(response);
            console.log("Candidate successfully created");
            window.location.replace("/applySuccess");
        },

        error: function (errMsg) {
            console.log(errMsg.toString())
        }
    });
}

function serializeFormData(form){

    var unindexed_array = form.serializeArray();
    var indexed_array = {};

    // save and serialize all form data excluding skills
    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    var tempCandidate = {
                            "applyData":
                            {
                                "position-id" : indexed_array['position-id'],
                                "first-name": indexed_array['first-name'],
                                "last-name": indexed_array['last-name'],
                                "resume": indexed_array['resume'],
                                "cover-letter": indexed_array['cover-letter'],
                                "notes": indexed_array['notes']
                            },
    };

    return tempCandidate;
}

function openResume(){
    document.getElementById("grey-out").style.display = "block";
    document.getElementById("pop-up-form").style.display = "block";
}

function closeResume(){
    document.getElementById("grey-out").style.display = "none";
    document.getElementById("pop-up-form").style.display = "none";
}