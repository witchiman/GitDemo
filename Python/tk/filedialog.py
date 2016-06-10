from tkinter import*
root = Tk()

def callback():
    filename = filedialog.askopenfilename(filetypes=[('PNG','png'),('PY','py'),
                                                     ('JPG','jpg')])
    print(filename)
Button(root,text='OPEN_FILE',command=callback).pack()

mainloop()
