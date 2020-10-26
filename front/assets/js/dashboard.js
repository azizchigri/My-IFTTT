var all_services = [];

function getNameWidgets()
{
    $.ajax({
        url: 'http://127.0.0.1:8080/about.json',
        type: 'GET',
        contentType: "application/json",
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', getCookie('authorization'));},
        complete: function(result, status) {
            if (status == 'success') {
                var respond = JSON.parse(result.responseText);
                for (let i = 0; i < respond.server.services.length; i++) {
                    all_services.push(respond.server.services[i]);
                }
                getUserInfo();
            } else {console.log("error");}
        }
    });
}

function create_action(name)
{
    var url_name = 'http://127.0.0.1:8080/actions/' + name;
    $.ajax({
        url: url_name,
        type: 'GET',
        contentType: 'application/json',
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', getCookie('authorization'));},
        complete: function (result, status) {
            if (status == "success")
            {
                var respond = JSON.parse(result.responseText);
                $.ajax({
                    url: 'http://127.0.0.1:8080/reactions',
                    type: 'GET',
                    contentType: 'application/json',
                    beforeSend: function(xhr){xhr.setRequestHeader('authorization', getCookie('authorization'));},
                    complete : function (result1, status1)
                    {
                        if (status1 == 'success') {
                            console.log(result1);
                            var respond_react = JSON.parse(result1.responseText);
                            setting_action(name, respond.params, respond.service, respond_react);
                        }
                    }
                });
            }
            else
                console.log("error");
        }
    });
}

function addWidget() {
    var id = parseInt(getCookie("widgetId"));
    id = id + 1;
    setCookie("widgetId", id.toString(), 10);
}

function elem_to_refresh_exist(i)
{
    document.getElementById("action" + i).innerHTML = "";
    document.getElementById("reaction" + i).innerHTML = "";
    let option_act = "";
    let option_react = "";
    let name = document.getElementById("temperatureModal" + i).getElementsByTagName("h4")[0].innerHTML;
    let found = false;
    for (let k = 0; k < subscribed_services.length; k++) {
        if (subscribed_services[k] == name)
            found = true;
    }
    if (found === true) {
        found = false;
        for (let j = 0; j < all_services.length; j++) {
            if (name === all_services[j].name)
                found = true;
            if (found === true) {
                found = false;
                for (let i = 0; i < all_services[j].actions.length; i++)
                    option_act += "<option>" + all_services[j].actions[i].name + "</option>";
            }
        }
    }
    for (let j = 0; j < all_services.length; j++) {
        for (let g = 0; g < all_services[j].reactions.length; g++) {
            let found = false;
            for (let k = 0; k < subscribed_services.length; k++){
                if (subscribed_services[k] === all_services[j].name)
                    found = true;
            }
            if (found === true)
                option_react += "<option>" + all_services[j].reactions[g].name + "</option>";
        }
    }
    document.getElementById("selectact"+i).innerHTML = "<option>Select an action</option>"+option_act;
    document.getElementById("selectreact"+i).innerHTML = "<option>Select an reaction</option>"+option_react;
}

function refresh_list_act_react()
{
    for (let i = 0; i < parseInt(getCookie("widgetId"), 10) + 1; i++)
    {
        if (document.getElementById("action" + i))
            elem_to_refresh_exist(i);
    }
}

function setting_action(name)
{
    var id = parseInt(getCookie("widgetId"), 10);
    id = id + 1;
    setCookie("widgetId", id.toString(), 10);
    var act_list = "<select id=selectact" + id + " name=" + id + " onchange='get_act_info(this)'><option>Select an action</option>";
    var react_list = "<select id=selectreact" + id + " name=" + id + " onchange='get_react_info(this)'><option>Select a reaction</option>";
    for (let j = 0; j < all_services.length; j++) {
        if (name === all_services[j].name) {
            for (let i = 0; i < all_services[j].actions.length; i++)
                act_list += "<option>" + all_services[j].actions[i].name + "</option>";
        }
        for (let i = 0; i < all_services[j].reactions.length; i++) {
            let found = false;
            for (let k = 0; k < subscribed_services.length; k++)
            {
                if (subscribed_services[k] === all_services[j].name)
                    found = true;
            }
            if (found === true)
                react_list += "<option>" + all_services[j].reactions[i].name + "</option>";
        }
    }
    act_list += "</select>";
    react_list += "</select>";
    var grids = $('.grid-stack').data('gridstack');
    grids.addWidget( jQuery( '<div class="ui-draggable ui-resizable ui-resizable-autohide bg-info text-black .text-center" id="widgetTemperature' + getCookie("widgetId") + '">' +
        '<div class="grid-stack-item-content bg-dark text-white" id="widget' + getCookie("widgetId") +'" name=' + name + '> '+
        '<p>'+name+'</p>' +
        '<p id=set' + getCookie("widgetId") + '>Not set yet</p>' +
        '<button class="btn pull-right widget-config" name="'+ getCookie("widgetId") +'" onclick="deleteAction(this)">' +
        '<i class="glyphicon glyphicon-remove"></i>' +
        '</button>' +
        '<button id="setactreactbutton' + getCookie("widgetId") + '" class="btn pull-right widget-config" data-toggle="modal" href="#temperatureModal' + getCookie("widgetId") + '">' +
        '<i class="glyphicon glyphicon-cog"></i>' +
        '</button> </div> <div  id="widgetDisplay' + getCookie("widgetId") + '"></div></div>' ), 0, 0, 2, 2, true);
    var modal = '<div class="modal fade" id="temperatureModal' + getCookie("widgetId") + '" role="dialog">' +
        '    <div class="modal-dialog">' +
        '        <div class="modal-content" name="' + getCookie("widgetId") + '">' +
        '            <div class="modal-header" >' +
        '                <h4 class="modal-title text-center">' + name + '</h4>' +
        '            </div>' +
        '            <div class="modal-body">' +
        '<p>ACTION</p><br>'+ act_list + '<div id="action' + getCookie("widgetId") + '"></div>' +
        '<p>REACTION</p><br>' + react_list +
        '            </div>' +
        '            <div id="reaction' + getCookie("widgetId") + '">' +
        '            </div>' +
        '            <div class="modal-footer" id="errorDisplayTemperature' + getCookie("widgetId") + '">' +
        '                <div class="container-fluid">' +
        '                    <button type="submit" class="btn btn-primary pull-right" name="' + getCookie("widgetId") + '" onclick="launch_act_react(' + getCookie("widgetId") + ')">Finish</button>' +
        '                </div>' +
        '                <p id="resultactreact'+getCookie("widgetId")+'"></p>' +
        '            </div>' +
        '        </div>' +
        '    </div>' +
        '</div>';
    $("body").append(modal);
}

function deleteAction(elem){
    $("#temperatureModal" + elem.name).remove();
    $("#widgetTemperature" + elem.name).remove();
    var save = JSON.parse(getCookie('save'));
    save.widget.splice(elem.name, 1);
    setCookie("save", JSON.stringify(save), 10);
}

function get_act_info(data)
{
    get_act_param(data.value, data.name);
}

function get_act_param(name, id)
{
    if (name === "Select an action") {
        document.getElementById("action"+id).innerHTML = react;
        return;
    }
    var url_name = 'http://127.0.0.1:8080/actions/' + name;
    $.ajax({
        url: url_name,
        type: 'GET',
        contentType: 'application/json',
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', getCookie('authorization'));},
        complete: function (result, status) {
            if (status == "success")
            {
                var input = JSON.parse(result.responseText);
                var desc = "";
                for (let i = 0; i < all_services.length; i++)
                {
                    for (let j = 0; j < all_services[i].actions.length; j++)
                    {
                        if (name === all_services[i].actions[j].name)
                            desc = all_services[i].actions[j].description;
                    }
                }
                var react_service = input.service;
                input = input.params;
                var react = "<p>" + react_service + " : " + desc + "</p>";
                for (let i = 0; i < input.length; i++)
                {
                    let type = "number";
                    if (input[i].type == "Date")
                        type = "date";
                    else if (input[i].type == "String")
                        type = "text";
                    react += "<div class='input-group'><p>"+ input[i].name + "</p><input id=" + input[i].name + " type=" + type + "></div>";
                }
                document.getElementById("action"+id).innerHTML = react;
            }
            else
                console.log("error");
        }
    });
}

function get_react_info(data)
{
    get_react_param(data.value, data.name);
}

function get_react_param(name, id)
{
    console.log(all_services);
    if (name === "Select a reaction") {
        document.getElementById("reaction" + id).innerHTML = "";
        return;
    }
    var url_name = 'http://127.0.0.1:8080/reactions/' + name;
    $.ajax({
        url: url_name,
        type: 'GET',
        contentType: 'application/json',
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', getCookie('authorization'));},
        complete: function (result, status) {
            if (status == "success")
            {
                var input = JSON.parse(result.responseText);
                var desc = "";
                for (let i = 0; i < all_services.length; i++)
                {
                    for (let j = 0; j < all_services[i].reactions.length; j++)
                    {
                        if (name === all_services[i].reactions[j].name)
                            desc = all_services[i].reactions[j].description;
                    }
                }
                var react_service = input.service;
                input = input.params;
                var react = "<p>" + react_service + " : " + desc + "</p>";
                for (let i = 0; i < input.length; i++)
                {
                    let type = "number";
                    if (input[i].name == "privacy")
                    {
                        console.log("it's here");
                        react += "<div class='input-group'><p>"+ input[i].name + "</p><select id=" + input[i].name + " name=" + input[i].type + "><option>private</option><option>public</option><option>unlisted</option></select></div>";
                    }
                    else
                    {
                        if (input[i].type == "Date")
                            type = "date";
                        else if (input[i].type == "String")
                            type = "text";
                        react += "<div class='input-group'><p>"+ input[i].name + "</p><input id=" + input[i].name + " name=" + input[i].type + " type=" + type + "></div>";
                    }
                }
                document.getElementById("reaction"+id).innerHTML = react;
            }
            else
                console.log("error");
        }
    });
}

function launch_act_react(id)
{
    var msg = {
        name: "",
        serviceName: "",
        user: null,
        reaction: {
            name: "",
            serviceName: "",
            parameters: []
        },
        parameters: []
    };
    var name_act = document.getElementById("selectact" + id).value;
    var name_react = document.getElementById("selectreact" + id).value;
    var act = document.getElementById("action" + id).getElementsByTagName("input");
    var react = document.getElementById("reaction" + id).getElementsByTagName("input");
    msg.name = name_act;
    msg.reaction.name = name_react;
    for (let i = 0; i < all_services.length; i++)
    {
        for (let j = 0; j < all_services[i].actions.length; j++)
        {
            if (name_act === all_services[i].actions[j].name)
                msg.serviceName = all_services[i].name;
        }
    }
    for (let i = 0; i < all_services.length; i++)
    {
        for (let j = 0; j < all_services[i].reactions.length; j++)
        {
            if (name_react === all_services[i].reactions[j].name)
                msg.reaction.serviceName = all_services[i].name;
        }
    }
    for (let i = 0; i < act.length; i++)
    {
        let input = {id: 0, name: "", type: "", value: ""};
        input.name = "" + act[i].id;
        input.type = "" + act[i].name;
        input.value = "" + act[i].value;
        if (act[i].type == "date")
            input.value = dateToJavadate(input.value);
        msg.parameters.push(input);
    }
    for (let i = 0; i < react.length; i++)
    {
        let input = {id: 0, name: "", type: "", value: ""};
        input.name = "" + react[i].id;
        input.type = "" + react[i].name;
        input.value = "" + react[i].value;
        if (react[i].type == "date")
            input.value = dateToJavadate(input.value);

        msg.reaction.parameters.push(input);
    }
    let priv = document.getElementById("reaction" + id).getElementsByTagName("select");
    if (priv.length != 0)
    {
        let input = {id: 0, name: "", type: "", value: ""};
        input.name = "" + priv[0].id;
        input.type = "" + priv[0].name;
        input.value = "" + priv[0].value;
        msg.reaction.parameters.push(input);
    }
    send_action(JSON.stringify(msg), id);
}

function dateToJavadate(rdv) {
    let datt = "" + new Date();
    datt = datt.split(" ");
    let clock = datt[4];
    let GMT = "";
    let str = datt[5];
    for (let i = 3; i < str.length; i++)
      GMT += str[i];
    datt = rdv +"T"+clock+GMT;
    console.log(datt);
    return (datt);   
}

function send_action(data, id)
{
    var url_name = 'http://127.0.0.1:8080/actions';
    $.ajax({
        url: url_name,
        type: 'POST',
        contentType: 'application/json',
        data: data,
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', getCookie('authorization'));},
        complete: function (result, status) {
            if (status == "success")
            {
                result = JSON.parse(result.responseText);
                let str = "set"+id;
                document.getElementById(str).innerHTML = "<p>Action: "+result.name+"</p><br><p>Reaction : "+ result.reaction.name +"</p>";
                str = "temperatureModal" + id;
                document.getElementById("setactreactbutton"+id).outerHTML = "";
                $("#"+str).modal("hide");
            }
            else
            {
                document.getElementById("resultactreact" + id).innerHTML = "NOT VALIDE";
                document.getElementById("set"+id).innerHTML = "Error setting";
            }
        }
    });
}

$(function () {
    var options = {
        cellHeight: 80,
        verticalMargin: 10
    };
    $('.grid-stack').gridstack(options);
});
