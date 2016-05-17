$(function () {
    $('#summernote').summernote({
        height: 300,                 // set editor height
        minHeight: null,             // set minimum height of editor
        maxHeight: null,             // set maximum height of editor
        focus: true,                 // set focus to editable area after initializing summernote
        callbacks: {
            onImageUpload: function (files) {
                // upload image to server and create imgNode...
                for (var i = 0; i < files.length; i++)
                    sendFile(files[i]);
            }
        }
    });
});

function sendFile(file) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var data = new FormData();
    data.append("file", file);
    $.ajax({
        url: "/upload/images",
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',
        beforeSend: function(xhr) {
            // here it is
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            $('#summernote').summernote('insertImage', data.assets.realPath, data.assets.name);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus + " " + errorThrown);
        }

    })
}