package com.umeshghimire.commons.helpers

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.umeshghimire.commons.extensions.getIntValue
import com.umeshghimire.commons.extensions.getStringValue
import com.umeshghimire.commons.models.SimpleContact

// used for sharing privately stored contacts in Simple Contacts with Simple Dialer and Simple SMS Messenger
class MyContactsContentProvider {
    companion object {
        private const val AUTHORITY = "com.umeshghimire.commons.contactsprovider"
        val CONTACTS_CONTENT_URI = Uri.parse("content://$AUTHORITY/contacts")

        const val COL_RAW_ID = "raw_id"
        const val COL_CONTACT_ID = "contact_id"
        const val COL_NAME = "name"
        const val COL_PHOTO_URI = "photo_uri"
        const val COL_PHONE_NUMBER = "phone_number"

        fun getSimpleContacts(context: Context, cursor: Cursor?): ArrayList<SimpleContact> {
            val contacts = ArrayList<SimpleContact>()
            val packageName = context.packageName.removeSuffix(".debug")
            if (packageName != "com.umeshghimire.dialer" && packageName != "com.umeshghimire.smsmessenger") {
                return contacts
            }

            try {
                cursor?.use {
                    if (cursor.moveToFirst()) {
                        do {
                            val rawId = cursor.getIntValue(COL_RAW_ID)
                            val contactId = cursor.getIntValue(COL_CONTACT_ID)
                            val name = cursor.getStringValue(COL_NAME)
                            val photoUri = cursor.getStringValue(COL_PHOTO_URI)
                            val phoneNumber = cursor.getStringValue(COL_PHONE_NUMBER)
                            val contact = SimpleContact(rawId, contactId, name, photoUri, phoneNumber)
                            contacts.add(contact)
                        } while (cursor.moveToNext())
                    }
                }
            } catch (ignored: Exception) {
            }
            return contacts
        }
    }
}
