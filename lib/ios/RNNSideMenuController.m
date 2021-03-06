//
//  RNNSideMenuController.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 09/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import "RNNSideMenuController.h"
#import "RNNSideMenuChildVC.h"
#import "MMDrawerController.h"

@interface RNNSideMenuController ()

@property (readwrite) RNNSideMenuChildVC *center;
@property (readwrite) RNNSideMenuChildVC *left;
@property (readwrite) RNNSideMenuChildVC *right;
@property (readwrite) MMDrawerController *sideMenu;

@end

@implementation RNNSideMenuController

-(instancetype)initWithControllers:(NSArray*)controllers;
{
	self = [super init];
	
	[self setControllers:controllers];
	
	self.sideMenu = [[MMDrawerController alloc] initWithCenterViewController:self.center leftDrawerViewController:self.left rightDrawerViewController:self.right];
	
	self.sideMenu.openDrawerGestureModeMask = MMOpenDrawerGestureModeAll;
	self.sideMenu.closeDrawerGestureModeMask = MMOpenDrawerGestureModeAll;
	
	[self addChildViewController:self.sideMenu];
	[self.sideMenu.view setFrame:self.view.bounds];
	[self.view addSubview:self.sideMenu.view];
	[self.view bringSubviewToFront:self.sideMenu.view];
	
	
	return self;
}

-(void)setControllers:(NSArray*)controllers {
	for (id controller in controllers) {
		
		if ([controller isKindOfClass:[RNNSideMenuChildVC class]]) {
			RNNSideMenuChildVC *child = (RNNSideMenuChildVC*)controller;
			
			if (child.type == RNNSideMenuChildTypeCenter) {
				self.center = child;
			}
			else if(child.type == RNNSideMenuChildTypeLeft) {
				self.left = child;
			}
			else if(child.type == RNNSideMenuChildTypeRight) {
				self.right = child;
			}
		}
		
		else {
			@throw [NSException exceptionWithName:@"UnknownSideMenuControllerType" reason:[@"Unknown side menu type " stringByAppendingString:[controller description]] userInfo:nil];
		}
	}
}

@end
