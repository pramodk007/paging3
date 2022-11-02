package com.leveloper.paging3

// sealed 클래스란 추상 클래스로 상속 받는 자식 클래스의 종류를 제한하는 특성.
// 다른곳에서 이 클래스를 사용할 때 딱 이 이것들 밖에 없다는 것을 암시해줌
sealed class SampleModel(val type: SampleType) {
    data class Data(val value: String): SampleModel(SampleType.DATA)
    data class Header(val title: String): SampleModel(SampleType.HEADER)
    object Separator: SampleModel(SampleType.SEPARATOR)
}

enum class SampleType {
    HEADER, DATA, SEPARATOR
}