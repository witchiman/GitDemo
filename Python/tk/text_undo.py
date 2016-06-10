from tkinter import*
root = Tk()

#设置autoseparators=False可单步撤消
text=  Text(root,width = 30,height = 5,undo=True,autoseparators=False)

text.pack()

text.insert(INSERT,'I love the world!')

def callback(event): #人为插入分隔符，可单步撤消  
    text.edit_separator()
text.bind('<Key>',callback)
def show():
    text.edit_undo()

Button(root,text='撤消',command=show).pack()

