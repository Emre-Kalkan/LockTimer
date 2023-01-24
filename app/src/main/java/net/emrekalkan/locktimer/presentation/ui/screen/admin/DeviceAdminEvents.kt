package net.emrekalkan.locktimer.presentation.ui.screen.admin

sealed class DeviceAdminEvents {
    object AdminRemoved: DeviceAdminEvents()
    object None: DeviceAdminEvents()
}