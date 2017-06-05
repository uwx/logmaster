echo $PATH
echo $PWD
if [ $PWD != '/home/jitpack/build' ]; then
    exit 0
fi

wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" https://nodejs.org/dist/v8.0.0/node-v8.0.0-linux-x64.tar.xz
tar xvf *.tar.xz
rm -f *.tar.xz

#mv node-v8.0.0-linux-x64/.* .
#mv node-v8.0.0-linux-x64/* .
#rmdir node-v8.0.0-linux-x64

#export PATH=/home/jitpack/build/node-v8.0.0-linux-x64/bin:$PATH
