package dev.boring.photo.enhance.common.data

data class UserStatus(
    var isPremium: Boolean,
    var freeDownloads: Int,
    var photoStatus: PhotoStatus
)

enum class PhotoStatus {
    LOADING, DOWNLOADED
}
