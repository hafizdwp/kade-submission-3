package me.hafizdwp.kade_submission_3.data.source.remote.model

/**
 * @author hafizdwp
 * 29/10/2019
 **/
data class ExampleResponse(
    var status: String?,
    var country: String?,
    var countryCode: String?,
    var region: String?,
    var regionName: String?,
    var city: String?,
    var zip: String?,
    var lat: Double?,
    var lon: Double?,
    var timezone: String?,
    var isp: String?,
    var org: String?,
    var `as`: String?,
    var query: String?
)