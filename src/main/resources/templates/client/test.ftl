<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Test</title>
    <script type="text/javascript" src="${'/vendor/jquery/jquery-3.3.1.min.js'}"></script>
    <script type="text/javascript" src="${'/vendor/ckeditor/ckeditor.js'}"></script>
    <script type="text/javascript" src="${'/vendor/ckfinder/ckfinder.js'}"></script>
    <script>
        // CKFinder
        function BrowseServer(startupPath) {
            let finder = new CKFinder();
            finder.basePath = '../';
            finder.startupPath = startupPath;
            finder.selectActionFunction = SetFileField;
            finder.popup();
        }

        function SetFileField(fileUrl) {
            console.log(fileUrl);
        }
    </script>
</head>
<body>

<input type="file" onclick="BrowseServer('Images:/');">
<br/>
<br/>
<textarea id="editor"></textarea>
<script>
    CKEDITOR.replace('editor', {
        filebrowserBrowseUrl: "${'/vendor/ckfinder/ckfinder.html'}",
        filebrowserImageBrowseUrl: "${'/vendor/ckfinder/ckfinder.html?type=Images'}",
        filebrowserFlashBrowseUrl: '/vendor/ckfinder/ckfinder.html?type=Flash',
        filebrowserUploadUrl: '/vendor/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
        filebrowserImageUploadUrl: '/vendor/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
        filebrowserFlashUploadUrl: '/vendor/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
    });
</script>
</body>
</html>