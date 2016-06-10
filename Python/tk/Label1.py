from tkinter import *

root = Tk()

photo = PhotoImage(file = '1.gif')
theLabel = Label(root,
                 text = '学无止境，哈哈哈哈',
                 image= photo,
                 compound = CENTER,
                 font= ('微软雅黑',20),
                 fg= 'white')

theLabel.pack()

mainloop()
