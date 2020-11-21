package at.kotlin.usermanager.exceptions

class BannedAccountException
    : Exception("Account wegen zu vielen Fehlversuchen deaktiviert")
