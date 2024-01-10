package com.example.api

object ApiRoutes {
    const val BASE = "/api"
    const val HEALTHCHECK = "$BASE/healthcheck"
    const val REGISTER = "$BASE/register"
    const val REGISTER_CREATE = "$REGISTER/create"
    const val LOGIN = "$REGISTER/login"

    object PressureRecord {
        const val ROOT = "$BASE/pressureRecord"
        const val ADD = ROOT
        const val DELETE = ROOT
        const val EDIT = ROOT
        const val GET_PAGINATED = ROOT
    }

    object Device {
        const val ROOT = "$BASE/device"
        const val ADD = ROOT
        const val GET_ALL = ROOT
        const val DELETE = ROOT
    }

    object User {
        const val ROOT = "$BASE/user"
        const val DELETE = "$ROOT/delete"
        const val EDIT = "$ROOT/edit"
    }

    object Tags {
        const val ROOT = "$BASE/tags"
        const val GET = ROOT
        const val ADD = ROOT
        const val DELETE = ROOT
        const val DELETE_ALL = "$ROOT/deleteAll"
    }

    object PressureRecordTagLinks {
        const val ROOT = "$BASE/pressureRecordTagLinks"
        const val ADD = ROOT
        const val DELETE_BY_RECORD = "$ROOT/deleteByRecord"
        const val DELETE_BY_TAG = "$ROOT/deleteByTag"
    }
}
