#!/bin/bash

Root=testFolders


function foo() {
    local dd=$1[@]
    local ff=$2[@]
    local drs=("${!dd}")
    local fls=("${!ff}")

    for dir in "${drs[@]}"
    do
        for file in "${fls[@]}"
        do
            touch $dir/$file
            echo ""Text in file $dir/$file"" > $dir/$file
        done
        # dd if=/dev/zero of=bigFile.txt bs=10m count=1
    done
}

mkdir ./$Root
cd $Root
rm -fr dir{1..2}
mkdir -p ./dir{1..2}/dir{3..5}/dir{6..10}

echo root/file1 > file1.txt
echo root/file2 > file2.txt


d=( dir{1..2} )
f=( file{C..F}.txt )

foo d f

d=( dir{1..2}/dir{3..5} )
f=( file{A..C}.txt )
foo d f

d=( dir{1..2}/dir{3..5}/dir{6..10} )
f=( file{B..D}.txt )
foo d f

d=( dir{1..2} )
f=( file{X..Z}.txt )
foo d f
cd ../

