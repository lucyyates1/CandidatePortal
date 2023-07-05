
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

    $(".loadingContainer").hide();
    // Disabling the page while an AJAX request is made
    $(document).ajaxStart(function () {
        $("*").css('pointer-events','none');
        $("*").css('overflow','hidden');
        $("body").css('background-color','var(--grey-light)');
        $("body").css('opacity','0.5');
        $(".loadingContainer").show();
    });
    // Enabling the page once the AJAX request returns
    $(document).ajaxStop(function () {
        $("*").css('pointer-events','');
        $("*").css('overflow','');
        $("body").css('background-color','');
        $("body").css('opacity','');
        $(".loadingContainer").hide();
    });

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    $("#addCandidateForm").submit(function (event) {
        event.preventDefault();
        post_candidate($(this));
    });
});

function post_candidate(form){

    var position_id = urlParams.get('id');   // retrieves the url param id of the position
    var actionUrl = "/positions/" + position_id + "/candidates";
    var data = serializeFormData(form);

    console.log(data)
    $.ajax({
        type: "POST",
        url: actionUrl,
        contentType: "application/json",
        datType: "json",
        processData: false,
        data: JSON.stringify(data),

        success: function (response, textStatus) {
            console.log(response);
            console.log("Candidate successfully created");
            window.location.replace("/candidatesposition?id=" + position_id);
        },

        error: function (errMsg) {
            console.log(errMsg.toString())
        }
    });
}

// serializes the form data into JSON in two separate associative arrays one for skills and another for all other data
function serializeFormData(form){

    var unindexed_array = form.serializeArray();
    var indexed_array = {};

    // save and serialize all form data excluding skills
    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    var tempCandidate = {
                            "candidate":
                            {
                                "first_name": indexed_array['first_name'],
                                "last_name": indexed_array['last_name'],
                                "resume_path": indexed_array['resume_path'],
                                "initial_contact_date": indexed_array['initial_contact_date'],
                                "meet_and_greet_date": indexed_array['meet_and_greet_date'],
                                "notes": indexed_array['notes']
                            },
                            "positionCandidate":
                            {

                            }
    };

    return tempCandidate;
}