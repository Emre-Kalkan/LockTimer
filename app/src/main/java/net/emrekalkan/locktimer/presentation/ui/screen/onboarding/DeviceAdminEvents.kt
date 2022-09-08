package net.emrekalkan.locktimer.presentation.ui.screen.onboarding

sealed class DeviceAdminEvents {
    object AdminRemoved: DeviceAdminEvents()
    object None: DeviceAdminEvents()
}