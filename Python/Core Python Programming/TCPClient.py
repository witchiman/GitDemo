from socket import *
from  time import ctime

ADDR = ('127.0.0.1',6789)
BUFSIZE = 1024
s = socket(AF_INET, SOCK_STREAM)
s.connect(ADDR)
while True:
    data = input('please input the data:')
    if not data:
        break
    s.send(data.encode())
    data = s.recv(BUFSIZE).decode()
    if not data:
        break
    print(data)

s.close()
