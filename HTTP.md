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

# Classic HTTP Servers

1. Tomcat
2. Jetty <-
3. Undertow <-

## HTTP/1.1 in depth

### Method

* GET: no body (0 bytes), no Content-Length (Content-Length: 0)
* POST: body (0+ bytes), Content-Length: x bytes
* PUT: body (0+ bytes), Content-Length: x bytes
* DELETE: body (0+ bytes), Content-Length: x bytes (WARNING!)

Transfer-Encoding: chunked

--- later ---
* PATCH
* OPTIONS
* HEAD
* TRACE
* ...
* CUSTOM

Limit size headers

RQ LINE + HEADERS\r\n
\r\n
BODY