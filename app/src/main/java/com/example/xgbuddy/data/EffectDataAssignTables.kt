package com.example.xgbuddy.data

object EffectDataAssignTables {
    val reverbTime: Array<String> = arrayOf(
        "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9",
        "1.0", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9",
        "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8", "2.9",
        "3.0", "3.1", "3.2", "3.3", "3.4", "3.5", "3.6", "3.7", "3.8", "3.9",
        "4.0", "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8", "4.9",
        "5.0", "5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5",
        "10.0", "11.0", "12.0", "13.0", "14.0", "15.0", "16.0", "17.0", "18.0",
        "19.0", "20.0", "25.0", "30.0",
    )

    val delayTime: Array<String> = arrayOf(
        "0.1", "1.7", "3.2", "4.8", "6.4", "8.0", "9.5", "11.1", "12.7", "14.3",
        "15.8", "17.4", "19.0", "20.6", "22.1", "23.7", "25.3", "26.9", "28.4",
        "30.0", "31.5", "33.2", "34.7", "36.3", "37.9", "39.5", "41.0", "42.6",
        "44.2", "45.7", "47.3", "48.9", "50.5", "52.0", "53.6", "55.2", "56.8",
        "58.3", "59.9", "61.5", "63.1", "64.6", "66.2", "67.8", "69.4", "70.9",
        "72.5", "74.1", "75.7", "77.2", "78.8", "80.4", "81.9", "83.5", "85.1",
        "86.7", "88.2", "89.8", "91.4", "93.0", "94.5", "96.1", "97.7", "99.3",
        "100.8", "102.4", "104.0", "105.6", "107.1", "108.7", "110.3", "111.9",
        "113.4", "115.0", "116.6", "118.2", "119.7", "121.3", "122.9", "124.4",
        "126.0", "127.6", "129.2", "130.7", "132.3", "133.9", "135.5", "137.0",
        "138.6", "140.2", "121.8", "143.3", "144.9", "146.5", "148.1", "149.6",
        "151.2", "152.8", "154.4", "155.9", "157.5", "159.1", "160.6", "162.2",
        "163.8", "165.4", "166.9", "168.5", "170.0", "171.7", "173.2", "174.8",
        "176.4", "178.0", "179.5", "181.1", "182.7", "184.3", "185.8", "187.4",
        "189.0", "190.6", "192.1", "193.7", "195.3", "196.9", "198.4", "200.0"
    )

    val eqFrequency: Array<String> = arrayOf(
        "20", "22", "25", "28", "30", "36", "40", "45", "50", "56", "63", "70", "80", "90",
        "100", "112", "125", "140", "160", "180", "200", "225", "250", "280", "315", "355",
        "400", "450", "500", "560", "630", "700", "800", "900", "1.0k", "1.1k", "1.2k", "1.4k",
        "1.6k", "1.8k", "2.0k", "2.2k", "2.5k", "2.8k", "3.2k", "3.6k", "4.0k", "4.5k", "5.0k",
        "5.6k", "6.3k", "7.0k", "8.0k", "9.0k", "10.0k", "11.0k", "12.0k", "14.0k", "16.0k",
        "18.0k", "20.0k"
    )

    val reverbDimension: Array<String> = arrayOf(
        "0.5", "0.8", "1.0", "1.3", "1.5", "1.8", "2.0", "2.3", "2.6", "2.8", "3.1", "3.3", "3.6",
        "3.9", "4.1", "4.4", "4.6", "4.9", "5.2", "5.4", "5.7", "5.9", "6.2", "6.5", "6.7", "7.0",
        "7.2", "7.5", "7.8", "8.0", "8.3", "8.6", "8.8", "9.1", "9.4", "9.6", "9.9", "10.2", "10.4",
        "10.7", "11.0", "11.2", "11.5", "11.8", "12.1", "12.3", "12.6", "12.9", "13.1", "13.4",
        "13.7", "14.0", "14.2", "14.5", "14.8", "15.1", "15.4", "15.6", "15.9", "16.2", "16.5",
        "16.8", "17.1", "17.3", "17.6", "17.9", "18.2", "18.5", "18.8", "19.1", "19.4", "19.7",
        "20.0", "20.2", "20.5", "20.8", "21.1", "21.4", "21.7", "22.0", "22.4", "22.7", "23.0",
        "23.3", "23.6", "23.9", "24.2", "24.5", "24.9", "25.2", "25.5", "25.8", "26.1", "26.5",
        "26.8", "27.1", "27.5", "27.8", "28.1", "28.5", "28.8", "29.2", "29.5", "29.9", "30.2"
    )

    val roomSize: Array<String> = arrayOf(
        "0.1", "0.3", "0.4", "0.6", "0.7", "0.9", "1.0", "1.2", "1.4", "1.5", "1.7", "1.8",
        "2.0", "2.1", "2.3", "2.5", "2.6", "2.8", "2.9", "3.1", "3.2", "3.4", "3.5", "3.7",
        "3.9", "4.0", "4.2", "4.3", "4.5", "4.6", "4.8", "5.0", "5.1", "5.3", "5.4", "5.6",
        "5.7", "5.9", "6.1", "6.2", "6.4", "6.5", "6.7", "6.8", "7.0"
    )

    val lfoFreq: Array<String> = arrayOf(
        "0.00", "0.04", "0.08", "0.13", "0.17", "0.21", "0.25", "0.29", "0.34", "0.38", "0.42",
        "0.46", "0.51", "0.55", "0.59", "0.63", "0.67", "0.72", "0.76", "0.80", "0.84", "0.88",
        "0.93", "0.97", "1.01", "1.05", "1.09", "1.14", "1.18", "1.22", "1.26", "1.30", "1.35",
        "1.39", "1.43", "1.47", "1.51", "1.56", "1.60", "1.64", "1.68", "1.72", "1.77", "1.81",
        "1.85", "1.89", "1.94", "1.98", "2.02", "2.06", "2.10", "2.15", "2.19", "2.23", "2.27",
        "2.31", "2.36", "2.40", "2.44", "2.48", "2.52", "2.57", "2.61", "2.65", "2.69", "2.78",
        "2.86", "2.94", "3.03", "3.11", "3.20", "3.28", "3.37", "3.45", "3.53", "3.62", "3.70",
        "3.87", "4.04", "4.21", "4.37", "4.54", "4.71", "4.88", "5.05", "5.22", "5.38", "5.55",
        "5.72", "6.06", "6.39", "6.73", "7.07", "7.40", "7.74", "8.08", "8.41", "8.75", "9.08",
        "9.42", "9.76", "10.10", "10.80", "11.40", "12.10", "12.80", "13.50", "14.10", "14.80",
        "15.50", "16.20", "16.80", "17.50", "18.20", "19.50", "20.90", "22.20", "23.60", "24.90",
        "26.20", "27.60", "28.90", "30.30", "31.60", "33.00", "34.30", "37.00", "39.70"
    )

    val modDelayOffset: Array<String> = arrayOf(
        "0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0", "1.1", "1.2",
        "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5",
        "2.6", "2.7", "2.8", "2.9", "3.0", "3.1", "3.2", "3.3", "3.4", "3.5", "3.6", "3.7", "3.8",
        "3.9", "4.0", "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8", "4.9", "5.0", "5.1",
        "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "6.0", "6.1", "6.2", "6.3", "6.4",
        "6.5", "6.6", "6.7", "6.8", "6.9", "7.0", "7.1", "7.2", "7.3", "7.4", "7.5", "7.6", "7.7",
        "7.8", "7.9", "8.0", "8.1", "8.2", "8.3", "8.4", "8.5", "8.6", "8.7", "8.8", "8.9", "9.0",
        "9.1", "9.2", "9.3", "9.4", "9.5", "9.6", "9.7", "9.8", "9.9", "10.0", "11.1", "12.2",
        "13.3", "14.4", "15.5", "17.1", "18.6", "20.2", "21.8", "23.3", "24.9", "26.5", "28.0",
        "29.6", "31.2", "32.8", "34.3", "35.9", "37.5", "39.0", "40.6", "42.2", "43.7", "45.3",
        "46.9", "48.4", "50.0",
    )

    val karaokeDelay: Array<String> = arrayOf(
        "0.1", "3.2", "6.4", "9.5", "12.7", "15.8", "19.0", "22.1", "25.3", "28.4", "31.6", "34.7",
        "37.9", "41.0", "44.2", "47.3", "50.5", "53.6", "56.8", "59.9", "63.1", "66.2", "69.4",
        "72.5", "75.7", "78.8", "82.0", "85.1", "88.3", "91.4", "94.6", "97.7", "100.9", "104.0",
        "107.2", "110.3", "113.5", "116.6", "119.8", "122.9", "126.1", "129.2", "132.4", "135.5",
        "138.6", "141.8", "144.9", "148.1", "151.2", "154.4", "157.5", "160.7", "163.8", "167.0",
        "170.1", "173.3", "176.4", "179.6", "182.7", "185.9", "189.0", "192.2", "195.3", "198.5",
        "201.6", "204.8", "207.9", "211.1", "214.2", "217.4", "220.5", "223.7", "226.8", "230.0",
        "233.1", "236.3", "239.4", "242.6", "245.7", "248.9", "252.0", "255.2", "258.3", "261.5",
        "264.6", "267.7", "270.9", "274.0", "277.2", "280.3", "283.5", "286.6", "289.8", "292.9",
        "296.1", "299.2", "302.4", "305.5", "308.7", "311.8", "315.0", "318.1", "321.3", "324.4",
        "327.6", "330.7", "333.9", "337.0", "340.2", "343.3", "346.5", "349.6", "352.8", "355.9",
        "359.1", "362.2", "365.4", "368.5", "371.7", "374.8", "378.0", "381.1", "384.3", "387.4",
        "390.6", "393.7", "396.9", "400.0",
    )
}