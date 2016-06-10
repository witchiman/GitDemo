#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from socket import *
from time import ctime

ADDR = ('',3456)
BUFSIZE = 1024
s = socket(AF_INET, SOCK_DGRAM)
s.bind(ADDR)

while True:
    print('waiting for message...')
    data,addr= s.recvfrom(BUFSIZE)
    data = data.decode('utf8')
    print(data,addr)
    s.sendto(('[%s]%s'%(ctime(),data)).encode('utf8'),addr)
    print('...received from and returned to ', addr)
s.close()
