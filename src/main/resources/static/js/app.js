
$('#updateFms').click(function () {
    //alert("Jquery works!");
    $.ajax({
        type: "GET",
        cache: false,
        url: '/parse',
        success: function (response) {
            var html = "File loaded to folder: <br>" + response + "<br>Database Updated successfully";
            $('#container').html(html);
            alert("Success! FMS table updated!");

            console.log(response);

        },
        error : function(e) {
            alert("Error!")
            console.log("ERROR: ", e);
        }
    });
});


$('#findFms').click(function () {
    if (!$("#data").val()) {
        alert("Enter your data!");
    } else {
        $.ajax({
            type: "POST",
            cache: false,
            url: '/getdep',


            data: {
                'code': $("#data").val()
            },
            success: function (response) {
                if (response){
                    var html = "<br>CODE: " + response.code + " Name: "+ response.name;
                    $('#container').html(html);
                }
                else{
                    var html = "<br> FMS with this code not Found!";
                    $('#container').html(html);

                }


            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }

});
