Forms:
- GET, http://localhost:8080/api/dump?login=Admin&password=secret&avatar=filename(not_file)

Parser -> Content-Type:
- POST (enctype/contentType: application/x-www-form-urlencoded), form body -> URLDecoder (no file, only filename)
- POST (enctype/contentType: text/plain), form body -> key=value\r\n (no file, only filename)
- POST (enctype/contentType: multipart/form-data), 

Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryvKRjL8fBfqKrNeS6\r\n
Headers: ...\r\n
\r\n
------WebKitFormBoundaryvKRjL8fBfqKrNeS6\r\n
Content-Disposition: form-data; name="login"\r\n
\r\n
Admin\r\n
------WebKitFormBoundaryvKRjL8fBfqKrNeS6\r\n
Content-Disposition: form-data; name="password"\r\n
\r\n
secret\r\n
------WebKitFormBoundaryvKRjL8fBfqKrNeS6\r\n
Content-Disposition: form-data; name="avatar"; filename="data.txt"\r\n
Content-Type: text/plain\r\n
\r\n
Top Secret\r\n
------WebKitFormBoundaryvKRjL8fBfqKrNeS6--\r\n


