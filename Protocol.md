1. Client (Request)
2. Server (Response)

Format (Request/Request):
1. Fixed Length: \[8 - meta]\[40 - message]\[8 - checksum] - 56 byte
2. Prefixed Length: \[8 - meta, 4 byte - size of message] (Postgres)
3. Delimiter \[..........................\r\n] - limit
4. *Connection close

HTTP/1.1: mixed 2 + 3