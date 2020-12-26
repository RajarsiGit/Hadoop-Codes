from fsplit.filesplit import FileSplit
fs = FileSplit(file='C:/Users/Administrator/eclipse-workspace/WordCountdemo/sample.txt', splitsize=2000000, output_dir='C:/Users/Administrator/eclipse-workspace/WordCountdemo/')
fs.split()
print("Success!")
