package com.aries.demo.widget.extendEditText


/**
 * Author wudaming
 * Created on 2018/6/5
 */

object NumberFormatterFactory {
    fun createNumberFormatter(formatType: FormatType): NumberFormatter {
        return when (formatType) {
            FormatType.NORMAL -> {
                NoFormatter()
            }
            FormatType.BANK -> {
                BankFormatter()
            }
            FormatType.PHONE -> {
                PhoneFormatter()
            }
        }
    }
}

enum class FormatType {
    NORMAL, PHONE, BANK;
}

interface NumberFormatter {
    /**
     * @param number 必须保证number的内容只有数字和分隔符
     */
    fun getFormattedText(number: String, separator: String): String
}


class NoFormatter : NumberFormatter {
    override fun getFormattedText(number: String, separator: String): String {
        return number
    }

}

class PhoneFormatter : NumberFormatter {

    override fun getFormattedText(number: String, separator: String): String {
        val phone = number.replace(separator, "")
        val resultText = StringBuilder()
        val length = phone.length
        when (length) {
            in 8..Int.MAX_VALUE -> {
                resultText.append(phone.substring(0, 3)).append(separator)
                resultText.append(phone.substring(3, 7)).append(separator)
                resultText.append(phone.substring(7, Math.min(length, 11)))
            }
            in 4..8 -> {
                resultText.append(phone.substring(0, 3)).append(separator)
                resultText.append(phone.substring(3, length))
            }
            in 0..4 -> {
                resultText.append(phone)
            }
        }
        return resultText.toString()
    }
}

class BankFormatter : NumberFormatter {

    /**
     * 步长可指定，暂时没有测试用例，也没有设计好如何使用，有这么个想法。
     */
    var stepLength = 4

    override fun getFormattedText(number: String, separator: String): String {
        val cardNumber = number.replace(separator, "")
        if (cardNumber.isEmpty()) {
            return ""
        }
        val resultText = StringBuilder()
        val length = cardNumber.length
        var pointer = 0
        if (length <= stepLength) {
            resultText.append(cardNumber)
        } else {
            for (i in stepLength..length step stepLength) {
                resultText.append(cardNumber.substring(pointer, i)).append(separator)
                pointer = i
            }
            if (length == pointer)
                resultText.deleteCharAt(resultText.lastIndex)
            else
                resultText.append(cardNumber.substring(pointer, length))
        }


        return resultText.toString()
    }

}
