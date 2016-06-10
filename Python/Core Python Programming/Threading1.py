from time import ctime,sleep
from threading import *
from random import randint

def foo(num, arg):
        i = 0
        while i < 5: 
                print('num%d: the arg is %s!\n'%(num,arg))
                sleep(randint(1,5))
                i+=1

t1 = Thread(target=foo, args=(1,'abcdedfg'))
t2 = Thread(target=foo, args=(2,'uvwwxyz'))

if __name__=='__main__':
        t1.start()
        t2.start()
        t1.join()
        t2.join()
        print('all the thing have been done!')
