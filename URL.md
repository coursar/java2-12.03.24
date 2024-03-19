domain|ip:port\(/path/path/path.html)\[?key=value&key=value]\[#fragment]

#fragment - didn't sent by browser

http -> 80 (tcp)
https -> 443 (tcp)

https://www.eldorado.ru/d/smartfony-i-gadzhety/ -> CatalogHandler
https://www.eldorado.ru/info/shops/11324/ -> ShopHandler


domain|ip:port\(/path/path/path.html)\[?key=value&key=value]\[#fragment]

Request-Line: _GET /path?key=value&key=value HTTP/1.1_: split(, maxsplit = 3)
Host: domain:port

\[0 -> path, 1 -> query] = pathAndQuery => split("?", maxsplit = 2)
Optional:
queryParts = query.split("&")
queryPart.split("=", 2)

https://www.eldorado.ru/search/catalog.php?q=iphone&strategy=advanced,zero_queries_predictor

&, ?... -> Percent Encoding
https://www.eldorado.ru/search/catalog.php?q=%D1%80%D1%83%D1%84%D0%BE%D0%BD&utf (browser lies)
