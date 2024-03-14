HTTP 0.9
HTTP 1.0
HTTP 1.1 <- Text
HTTP 2 <- Binary
--------------- (TCP)
HTTP 3 (UDP)

TEXT/BINARY

# HTTP1.1

Request
1. Request-Line
VERB /path HTTP/1.1\r\n
GET /NetBSD/README HTTP/1.1\r\n
2. Headers
Name: value\r\n
Accept: text/html\r\n

---- \r\n ------
3. Body

Response
1. Status-Line
   HTTP/1.1 STATUSCODE STATUSTEXT\r\n
   HTTP/1.1 200 OK\r\n
2. Headers
   Name: value\r\n
   Content-Type: text/html\r\n
   Content-Length: 8\r\n 

---- \r\n ------
3. Body - 8 byte