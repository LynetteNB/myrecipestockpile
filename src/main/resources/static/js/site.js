$(document).ready(function () {


    $('a.like-button').on('click', function () {
        $(this).toggleClass('liked');
    });


    const link = document.getElementById('url');
    $('#uploadpicture').click(function () {
        const client = filestack.init(AZvuYvrkS2GRaOBsg61tvz);
        client.pick({}).then(function (result) {
            const fileUrl = result.filesUploaded[0].url;
            link.href = fileUrl;
            link.innerHTML = fileUrl;
            console.log(fileUrl);
        });
    });



});


// const link = document.getElementById('url');
//
// const client = filestack.init(AZvuYvrkS2GRaOBsg61tvz);
// client.pick({
//
// }).then(function(result) {
//     const fileUrl = result.filesUploaded[0].url;
//     link.href=fileUrl;
//     link.innerHTML=fileUrl;
//     console.log(fileUrl);
// });
