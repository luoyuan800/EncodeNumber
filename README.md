# EncodeNumber

## Usage
 *Game data saving, Java Application data saving, Java cache and so on.*

## Example

        EncodeLong encodeLong = new EncodeLong(100);
        encodeLong.getValue(); //Get value
        encodeLong.setValue(1000); //Set value
        System.out.println(encodeLong);//toString() will println out the value

## Implement
It was a memory data encode library. You can use it on game data storage and cache.
As we know, a game or application that save data in local (PC, Android...). Game play can use 3rd tool to read or write those data. So Store data in memory or cache were insecurely.
Let's use this EncodeNumber to encrypt our application data. Then other people should not do any modify on our data!
The logic of this EncodeNumbe was not store data into cache directly, change thos number to binary and then using a random key to encrypt it.

## Performance test
I have done some performance load test on this library about read an write. It was enough for game or normal application nubmer data saving.

### EncodeLong:
    1000 read: 0.008           write: 0.004
    10000 read: 0.02           write: 0.016
    100000 read: 0.091         write: 0.064
    1000000 read: 0.747        write: 0.71
    10000000 read: 6.669       write: 6.755

### EncodeFloat:
    1000 read: 0.012 write: 0.014
    10000 read: 0.018 write: 0.02
    100000 read: 0.107 write: 0.121
    1000000 read: 0.632 write: 0.682
    10000000 read: 5.703 write: 6.922

