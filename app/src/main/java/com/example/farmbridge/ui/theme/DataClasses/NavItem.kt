package com.example.farmbridge.ui.theme.DataClasses

import android.media.Image
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.firebase.database.snapshot.CompoundHash.SplitStrategy
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label

data class NavItem(
    val label:String,
    val icon: ImageVector
)
