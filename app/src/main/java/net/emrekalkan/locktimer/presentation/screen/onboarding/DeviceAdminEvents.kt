package net.emrekalkan.locktimer.presentation.screen.onboarding

sealed class DeviceAdminEvents {
    object AdminRemoved: DeviceAdminEvents()
    object None: DeviceAdminEvents()
}