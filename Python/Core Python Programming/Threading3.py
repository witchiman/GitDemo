from time import ctime,sleep
from threading import *
from random import randint

class MyThread(Thread):
        def __init__(self, func, args,name=''):
                super(MyThread, self).__init__()
                self.name = name
                self.func = func
                self.args = args
        def run(self):
                self.res = self.func(*self.args)

def foo(num, arg):
        i = 0
        while i < 5: 
                print('num%d: the arg is %s!\n'%(num,arg))
                sleep(randint(1,5))
                i+=1

t1 = MyThread(foo, (1,'abcdefg'), foo.__name__)
t2 = MyThread(foo, (2,'uvwxyz'), foo.__name__)

if __name__== '__main__' :
        t1.start()
        t2.start()
        t1.join()
        t2.join()
        print('all the thing have been done!')
