 function setProgress(e){
    if(e.lengthComputable){
        let progress = parseInt(e.loaded / e.total * 100, 10);
        $("#modal-progress").setProgress(progress)
    }
}

function upload_dict_file(){
    let form_data = new FormData();
    let mdx_file = $('#mdx_file').prop('files');
    let mdd_file = $('#mdd_file').prop('files');
    form_data.append('mdx_file',mdx_file[0]);
    if(mdd_file.length) {
        form_data.append('mdd_file', mdd_file[0]);
    }
    $("#modal-progress").showDialog();
    $.ajax({
        type: 'POST',
        url: $("input[name=dict-upload-file]").val(),
        contentType:false,
        processData:false,
        data: form_data,
        cache: false,
        dataType: 'JSON',
        beforeSend: function (xhr, setting) {
            xhr.setRequestHeader("X-CSRFToken", $("input[name=csrfmiddlewaretoken]").val())
        },
         xhr: function() {
            var myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){
                myXhr.upload.addEventListener('progress',setProgress, false);
            }
            return myXhr;
        },
        success: function(data){
            console.info(data)
             $("#modal-progress").dismissDialog();
            if(data.is_valid){
                $(".upload-mdd h4").html("Click here to choose mdd file!")
                $(".upload-mdx h4").html("Click here to choose mdx file!")
            }
        },
        error: function(){
            console.info("error");
            $("#modal-progress").dismissDialog();
        }
    })
}

function check_upload_file(){
    let result=true
    let mdx_file = $('#mdx_file').prop('files');
    if(!mdx_file.length||!mdx_file[0].name.endsWith("mdx")){
        result=false
        $(".upload-mdx").addClass("animated shake")
        setTimeout(()=> $(".upload-mdx").removeClass("animated shake"), 800)
    }
    return result
}

$(document).ready(function(){
    $(".upload-mdx").click(()=> $('#mdx_file').click());
    $(".upload-mdd").click(()=>$("#mdd_file").click());
    $("#mdx_file").change((e)=> {
        if(e.currentTarget.value){
            $(".upload-mdx h4").html(e.currentTarget.value)
        } else {
            $(".upload-mdx h4").html("Click here to choose mdx file!")
        }
    } );
    $("#mdd_file").change((e)=> {
        if(e.currentTarget.value){
            $(".upload-mdd h4").html(e.currentTarget.value)
        } else {
            $(".upload-mdd h4").html("Click here to choose mdd file!")
        }
    } );
    $(".upload-action").click(()=> {
        if(check_upload_file()){
            upload_dict_file()
        }
    });
});