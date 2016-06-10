from socket import *
from  time import ctime

ADDR = ('',6789)
BUFSIZE = 1024
s = socket(AF_INET, SOCK_STREAM)
s.bind(ADDR)
s.listen(5)

try:
    while True:
        print('waiting for connection...')
        c,addr = s.accept()
        print('...connected from:',addr)
        while True:
            '''需编码和解码否则报错，TypeError: str
            does not support the buffer interface
             '''
            data = c.recv(BUFSIZE).decode() 
            if not data:
                break
            c.send(('[%s]%s'%(ctime(),data)).encode())
        c.close()

except (EOFError,KeyboardInterrupt) as e :
    s.close()
    print('服务已经关闭')
