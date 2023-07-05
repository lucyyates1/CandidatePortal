var nKeyID = 0; //Number of current keywords on the page; setting to 0 before applying, so first value is actually 1
var nPathID = -1; // decrementing before applying, so first value is actually 0
var certificationID = 0;
var certificationPathID = -1;
var skills_exist = false; // flag that denotes whether or not there are skills when saving
var skills_array = []; // holds all the position's skills to be saved
var certifications_exist = false; // flag that denotes whether or not there are certifications when saving
var certifications_array = []; //holds all tje position's certifications to be saved

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

    $("#add_skill").bind("click", addSkill);
    $("#add_certification").bind("click", addCertification)
    $("#position_template_menu").on("change", applyTemplateWrapper);

    $("#addPositionForm").submit(function (event) {
        event.preventDefault();
        post_position($(this));
    });
});

function addSkill()
{
    /* Adds dynamic input field and radio buttons for entering new skills or containing a template skill
    */

    /*
        This needs to be revised. Should create a row in a table element instead of an li / div.
        No labels inside the row (HTML has labels and primary structure for the table)
    */

    // track the ID for this keyword
    nKeyID++;
    nPathID++;

    // parent container element for our new keyword
    var parent = $("#skills_keywords_menu")[0]; // get a handle on the HTML div element for the extra skills
    var element = document.createElement("div");
    element.id = "keywordDiv" + nKeyID;
    element.className += " keywordRow"; // do not alter this line! Yes it's weird but it's how classes work
        // (the class is "keywordRow")
    parent.appendChild(element); // add this new keyword as a div into our parent div

    // create the text field
    const textField = document.createElement("input");
    textField.id="keywordText" + nKeyID;
    textField.name = "keywordText" + nKeyID;
    textField.className += " keywordText";
    textField.type="text";
    textField.path = "skillKeywords[" + nPathID + "].skillKeyword";
    element.appendChild(textField);

    // High priority radio button --%>
    const keywordRadioHigh = document.createElement("input");
    keywordRadioHigh.type = "radio";
    keywordRadioHigh.name = "keywordRadio" + nKeyID; // all radio buttons in a group need the same name!
    keywordRadioHigh.id = "keywordRadioHigh" + nKeyID;
    keywordRadioHigh.value = 3;
    keywordRadioHigh.path = "skillKeywords[" + nPathID + "].skillPriority";
    keywordRadioHigh.checked = true;
    element.appendChild(keywordRadioHigh);

    // Medium priority radio button --%>
    const keywordRadioMedium = document.createElement("input");
    keywordRadioMedium.type = "radio";
    keywordRadioMedium.name = "keywordRadio" + nKeyID;
    keywordRadioMedium.id = "keywordRadioMedium" + nKeyID;
    keywordRadioMedium.value = 2;
    keywordRadioMedium.path = "skillKeywords[" + nPathID + "].skillPriority";
    element.appendChild(keywordRadioMedium);

    // Low priority radio button --%>
    const keywordRadioLow = document.createElement("input");
    keywordRadioLow.type = "radio";
    keywordRadioLow.name = "keywordRadio" + nKeyID;
    keywordRadioLow.id = "keywordRadioLow" + nKeyID;
    keywordRadioLow.value = 1;
    keywordRadioLow.path = "skillKeywords[" + nPathID + "].skillPriority"; //nKeyID - 1 because array indexing starts at 0 instead of 1
    element.appendChild(keywordRadioLow);

    // remove keyword button --%>
    const removeKeyword = document.createElement("input");
    removeKeyword.type = "button";
    removeKeyword.name = "removeKeyword" + nKeyID;
    removeKeyword.id = "removeKeyword" + nKeyID;
    removeKeyword.value = "X";

    // remove row button function
    var temp = nKeyID; // record which ID we are so we can remove it in this function:
    $(document).on('click', '#removeKeyword' + nKeyID, function () {
        // shift all keywords' IDs that are to the right of here
        $('#keywordDiv' + temp).remove();
        console.log("removed ID " + temp); //testing
        // reset the entire list of keywords' paths to correlate in an ascending order (0, 1, 2, etc...)
        var k = 0;
        for (var i=1; i<=nKeyID; i++)
        {
            if ($("#keywordRadioLow"+i).length)
            {
                console.log("changed path for ID '" + i + " to ': '" + k + "'");
                // update all path IDs inside the div
                $('#keywordRadioLow' +i).attr('path','skillKeywords[' + k + '].skillPriority'); //i-2 because buttons start at 1, indexing for array starts at 0
                $('#keywordRadioMedium' +i).attr('path','skillKeywords[' + k + '].skillPriority');
                $('#keywordRadioHigh' +i).attr('path','skillKeywords[' + k + '].skillPriority');
                $('#keywordText' +i).attr('path','skillKeywords[' + k + '].skillKeyword');
                k++;
            }
        }
        nPathID--;
        skills_array = [];
    });
    element.appendChild(removeKeyword);

    const br0 = document.createElement("br");
    br0.id = "keywordBR0_" + nKeyID;
    element.appendChild(br0);
}

function addCertification()
{
        /* Adds dynamic input field and radio buttons for entering new certifications or containing a template skill
        */

        // track the ID for this keyword
        certificationID++;
        certificationPathID++;

        // parent container element for our new keyword
        var parent = $("#certifications_keywords_menu")[0]; // get a handle on the HTML div element for the extra skills
        var element = document.createElement("div");
        element.id = "certificationDiv" + certificationID;
        element.className += " keywordRow"; // do not alter this line! Yes it's weird but it's how classes work
            // (the class is "keywordRow")
        parent.appendChild(element); // add this new keyword as a div into our parent div

        // create the text field
        const textField = document.createElement("input");
        textField.id="certificationText" + certificationID;
        textField.name = "certificationText" + certificationID;
        textField.className += " certificationText";
        textField.type="text";
        textField.path = "certifications[" + certificationPathID + "].certification";
        element.appendChild(textField);

        // High priority radio button --%>
        const certificationRadioHigh = document.createElement("input");
        certificationRadioHigh.type = "radio";
        certificationRadioHigh.name = "certificationRadio" + certificationID; // all radio buttons in a group need the same name!
        certificationRadioHigh.id = "certificationRadioHigh" + certificationID;
        certificationRadioHigh.value = 3;
        certificationRadioHigh.path = "certifications[" + certificationPathID + "].certifications";
        certificationRadioHigh.checked = true;
        element.appendChild(certificationRadioHigh);

        // Medium priority radio button --%>
        const certificationRadioMedium = document.createElement("input");
        certificationRadioMedium.type = "radio";
        certificationRadioMedium.name = "certificationRadio" + certificationID;
        certificationRadioMedium.id = "certificationRadioMedium" + certificationID;
        certificationRadioMedium.value = 2;
        certificationRadioMedium.path = "certifications[" + certificationPathID + "].certification";
        element.appendChild(certificationRadioMedium);

        // Low priority radio button --%>
        const certificationRadioLow = document.createElement("input");
        certificationRadioLow.type = "radio";
        certificationRadioLow.name = "certificationRadio" + certificationID;
        certificationRadioLow.id = "certificationRadioLow" + certificationID;
        certificationRadioLow.value = 1;
        certificationRadioLow.path = "certifications[" + certificationPathID + "].certification";
        element.appendChild(certificationRadioLow);

        // remove keyword button --%>
        const removeCertification = document.createElement("input");
        removeCertification.type = "button";
        removeCertification.name = "removeCertification" + certificationID;
        removeCertification.id = "removeCertification" + certificationID;
        removeCertification.value = "X";

        // remove row button function
        var temp = certificationID; // record which ID we are so we can remove it in this function:
        $(document).on('click', '#removeCertification' + certificationID, function () {
            // shift all keywords' IDs that are to the right of here
            $('#certificationDiv' + temp).remove();
            console.log("removed ID " + temp); //testing
            // reset the entire list of keywords' paths to correlate in an ascending order (0, 1, 2, etc...)
            var k = 0;
            for (var i=1; i<=certificationID; i++)
            {
                if ($("#certificationRadioLow"+i).length)
                {
                    console.log("changed path for ID '" + i + " to ': '" + k + "'");
                    // update all path IDs inside the div
                    $('#certificationRadioLow' +i).attr('path','certifications[' + k + '].certification'); //i-2 because buttons start at 1, indexing for array starts at 0
                    $('#certificationRadioMedium' +i).attr('path','certifications[' + k + '].certification');
                    $('#certificationRadioHigh' +i).attr('path','certifications[' + k + '].certification');
                    $('#certificationText' +i).attr('path','certifications[' + k + '].certification');
                    k++;
                }
            }
            nPathID--;
            certifications_array = [];
        });
        element.appendChild(removeCertification);

        const br0 = document.createElement("br");
        br0.id = "certificationBR0_" + certificationID;
        element.appendChild(br0);
}

function applyTemplateWrapper()
{
    /* wrapper for applyPositionTemplate() */
    var scope = $("#position_template_menu")[0];
    var newVal = scope.options[scope.selectedIndex].value;
    if (scope.selectedIndex == 0)
    {
        $.data(scope,'value',newVal);
        return;
    }
    else if(confirm("Are you sure? Selecting 'OK' will wipe all current changes INCLUDING additional skills"))
    {
        $.data(scope,'value',newVal);
        applyPositionTemplate(newVal);
    }
    else
    {
        /*if($.data(scope,'value') == "")
            scope.value = "- Select Template -";
        else*/
        scope.value = $.data(scope,'value');
    }
}
//Applies template to input text boxes
// (position_name, skillKeywords, description, candidateResumePath, placeOfPerformance, placePerformancePriority, position_date)
function applyPositionTemplate(selectedTemplateId)
{
    // Cache the desired template object
    for(var i = 0; i < templatePositions.length; i++)
    {
        if(templatePositions[i].position_id == selectedTemplateId)
        {
            var templateObject = templatePositions[i];
            break;
        }
    }

    console.log(JSON.stringify(templateObject));

    // TODO: sanitize innerHTML for security (don't just allow any raw HTML)
        // what we really need is to ensure that no (malicious) HTML gets into the database in the first place.
    $('#position_name_field').val(templateObject.name);
    $('#position_description_field').val(templateObject.description);
    $('#position_description_field').scrollTop();
    //wipe all previous skills
    for(; nKeyID>0; nKeyID--)
    {
        $('#keywordDiv' + nKeyID).remove();
    }
    nPathID = -1; // reset to starting value
    //wipe all previous certifications
    for(; certificationID>0; certificationID--)
    {
        $('#certificationDiv' + certificationID).remove();
    }
    certificationPathID = -1; // reset to starting value

    // fill "Skill Keywords" with template skills
    for (let i = 0; i < Object.keys(templateObject.position_skills).length; i++)
    {
        addSkill();

        // Add keyword/skill name
        $('#keywordText' + nKeyID).val(templateObject.position_skills[i].skill.name);
        // Select proper radio button for priority
        if (templateObject.position_skills[i].priority == 1) // low
        {
            $("#keywordRadioLow"+ (i+1)).prop('checked', true);
        }
        else if (templateObject.position_skills[i].priority == 2) // medium
        {
            $("#keywordRadioMedium"+ (i+1)).prop('checked', true);
        }
        else if (templateObject.position_skills[i].priority == 3) // high
        {
            $("#keywordRadioHigh"+ (i+1)).prop('checked', true);
        }
    }

        // fill "Certification Keywords" with template certifications
        for (let i = 0; i < Object.keys(templateObject.position_certifications).length; i++)
        {
            addCertification();

            // Add keyword/certification name
            $('#certificationText' + certificationID).val(templateObject.position_certifications[i].certification.name);
            // Select proper radio button for priority
            if (templateObject.position_certifications[i].priority == 1) // low
            {
                $("#certificationRadioLow"+ (i+1)).prop('checked', true);
            }
            else if (templateObject.position_certifications[i].priority == 2) // medium
            {
                $("#certificationRadioMedium"+ (i+1)).prop('checked', true);
            }
            else if (templateObject.position_certifications[i].priority == 3) // high
            {
                $("#certificationRadioHigh"+ (i+1)).prop('checked', true);
            }
        }
}

// Save's multiple skills
function save_skills(position_id){
    for (let skill of skills_array)
        post_skill(position_id, skill);
}

// Save's multiple certifications
function save_certifications(position_id){
    for (let certification of certifications_array)
        post_certification(position_id, certification);
}

// Post request to save a position
function post_position(form){

    var actionUrl = form.attr('action');
    var data = serializeFormData(form);

    $.ajax({
        type: "POST",
        url: actionUrl,
        contentType: "application/json",
        datType: "json",
        processData: false,
        data: JSON.stringify(data),

        success: function (response, textStatus) {
            console.log("Position successfully created");
            if (skills_exist)
                save_skills(response.position_id);
            if (certifications_exist)
                save_certifications(response.position_id);
            window.location.replace("/recruiting");
        },

        error: function (errMsg) {
            console.log(errMsg.toString())
        }
    });
}

// Post request to save a skill
function post_skill(position_id, data){
        var url = "/positions/" + position_id + "/skills";

        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json",
            datType: "json",
            processData: false,
            data: JSON.stringify(data),

            success: function (response, textStatus) {
                console.log(response);
                console.log("Skill successfully created");
            },

            error: function (errMsg) {
                console.log(errMsg.toString())
            }
        });
}

// Post request to save a certification
function post_certification(position_id, data){
        var url = "/positions/" + position_id + "/certifications";

        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json",
            datType: "json",
            processData: false,
            data: JSON.stringify(data),

            success: function (response, textStatus) {
                console.log(response);
                console.log("Certification successfully created");
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
    var filling_keyword = false;

    // serialize all form data excluding skills and certifications
    $.map(unindexed_array, function(n, i){
        if (n['name'].includes("keyword"))
            skills_exist = true;
        else if (n['name'].includes("certification"))
            certifications_exist = true;
        else
            indexed_array[n['name']] = n['value'];
    });

    // serialize all skills
    var skill_name = "";
    for (let element of unindexed_array){
        var tempSkill = {
                            "skill":
                             {
                                "name": ""
                             },
                             "positionSkill":
                             {
                                "priority": undefined
                             }
                        };
        if (element.name.includes("keyword") && !filling_keyword){
            filling_keyword = true;
            skill_name = element.value;
            continue;
        }
        else if (element.name.includes("keyword") && filling_keyword){
            filling_keyword = false;
            tempSkill.skill.name = skill_name;
            tempSkill.positionSkill.priority = element.value;
            skills_array.push(tempSkill);
        }
    }

    filling_keyword = false; // resetting filling keyword
    // serialize all certifications
    var certification_name = "";
    for (let element of unindexed_array){
        var tempCertification = {
                            "certification":
                             {
                                "name": ""
                             },
                             "positionCertification":
                             {
                                "priority": undefined
                             }
                        };
        if (element.name.includes("certification") && !filling_keyword){
            filling_keyword = true;
            certification_name = element.value;
            continue;
        }
        else if (element.name.includes("certification") && filling_keyword){
            filling_keyword = false;
            tempCertification.certification.name = certification_name;
            tempCertification.positionCertification.priority = element.value;
            certifications_array.push(tempCertification);
        }
    }

    console.log(certifications_array);

    indexed_array.template = (indexed_array.template == "on") ? true : false;
    indexed_array.education_required = (indexed_array.education_required == "on") ? true : false;

    return indexed_array;
}
